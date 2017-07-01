package org.embulk.gradle.plugins

import org.embulk.gradle.plugins.task.PluginJar
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

class EmbulkPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.plugins.apply JavaPlugin
        project.tasks.create("pluginJar", PluginJar.class)
    }
}