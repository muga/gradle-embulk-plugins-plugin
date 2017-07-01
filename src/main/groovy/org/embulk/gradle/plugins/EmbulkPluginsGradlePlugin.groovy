package org.embulk.gradle.plugins

import org.embulk.gradle.plugins.task.PluginJar
import org.gradle.api.Plugin
import org.gradle.api.Project

class EmbulkPluginsGradlePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.apply(plugin: 'java')
        project.tasks.create("pluginJar", PluginJar.class)
    }
}