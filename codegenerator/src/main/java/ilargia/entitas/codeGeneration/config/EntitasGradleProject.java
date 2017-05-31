package ilargia.entitas.codeGeneration.config;


import ilargia.entitas.codeGeneration.CodeGenerator;
import ilargia.entitas.codeGeneration.interfaces.ICodeDataProvider;
import ilargia.entitas.codeGeneration.interfaces.ICodeGenFilePostProcessor;
import ilargia.entitas.codeGeneration.interfaces.ICodeGenerator;
import ilargia.entitas.gradle.CodeGenerationPluginExtension;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static ilargia.entitas.codeGeneration.CodeGeneratorUtil.*;

public class EntitasGradleProject implements ProjectPreferences {
    private static Properties prop = new Properties();
    private static InputStream input = null;
    private static OutputStream output = null;
    private final Project project;
    private final JavaPluginConvention javaConvention;
    private CodeGenerationPluginExtension extension;


    public EntitasGradleProject(Project gradleProject) {
        this.project = gradleProject;
        //this.project.getPlugins().apply(JavaPlugin.class);
        javaConvention = project.getConvention().getPlugin(JavaPluginConvention.class);
        extension = project.getExtensions().findByType(CodeGenerationPluginExtension.class);
        if (extension == null) {
            extension = new CodeGenerationPluginExtension();
        }


    }

    @Override
    public CodeGenerator getCodeGenerator() {
        Properties properties = loadProperties();
        CodeGeneratorConfig config = new CodeGeneratorConfig();
        config.configure(properties);
        List<Class> types = loadTypesFromPlugins(properties);

        List<ICodeDataProvider> dataProviders = getEnabledInstances(types, config.getDataProviders(), ICodeDataProvider.class);
        List<ICodeGenerator> codeGenerators = getEnabledInstances(types, config.getCodeGenerators(), ICodeGenerator.class);
        List<ICodeGenFilePostProcessor> postProcessors = getEnabledInstances(types, config.getPostProcessors(), ICodeGenFilePostProcessor.class);

        configure(dataProviders, properties);
        configure(codeGenerators, properties);
        configure(postProcessors, properties);

        return new CodeGenerator(dataProviders, codeGenerators, postProcessors);
    }


    @Override
    public String getProjectRoot() {
        return project.getRootProject().toString();
    }

    @Override
    public String getProjectName() {
        return project.getName();
    }

    @Override
    public String getProjectDir() {
        return project.getProjectDir().getAbsolutePath();
    }

    @Override
    public Set<File> getSrcDirs() {
        SourceSetContainer sourceSets = javaConvention.getSourceSets();
        SourceSet mainSourceSet = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);
        return mainSourceSet.getAllSource().getSrcDirs();

    }

    @Override
    public String getFirtsSrcDir() {
        try {
            return getSrcDirs().iterator().next().getCanonicalPath();
        } catch (IOException e) {
            System.out.println("Error no se encuentra el directorio de fuentes");
        }
        return "";

    }

    @Override
    public boolean hasProperties() {
        return new File(getProjectDir() + "/" + extension.getConfigFile()).exists();
    }

    @Override
    public Properties loadProperties() {
        if (hasProperties()) {
            try {
                EntitasGradleProject.prop.load(new FileInputStream(getProjectDir() + "/" + extension.getConfigFile()));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (EntitasGradleProject.input != null) {
                    try {
                        EntitasGradleProject.input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return EntitasGradleProject.prop;
    }

    @Override
    public void saveProperties(Properties properties) {
        try {
            properties.store(new FileOutputStream(getProjectDir() + "/" + extension.getConfigFile()),
                    "Entitas codeGeneration config file");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (EntitasGradleProject.output != null) {
                try {
                    EntitasGradleProject.output.close();
                } catch (Exception e) {
                    System.out.printf(e.getMessage());
                }
            }

        }
    }

}