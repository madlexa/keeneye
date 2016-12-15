/*
 * Copyright 2016 Aleksey Dobrynin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package one.trifle.keeneye

import groovy.transform.CompileStatic
import one.trifle.lurry.GQueryTemplate
import one.trifle.lurry.parser.YamlParser
import one.trifle.lurry.reader.FileReader
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.ClassPathResource

import javax.sql.DataSource

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
@CompileStatic
class SpringBootWebApplication extends SpringBootServletInitializer {

    static void main(String[] args) {
        SpringApplication.run(SpringBootWebApplication, args)
        println "Let's inspect the beans provided by Spring Boot:"
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "lurry.datasource")
    DataSource dataSource() {
        DataSourceBuilder.create().build()
    }

    @Bean
    GQueryTemplate lurry(DataSource dataSource) {
        // TODO resource by sql driver
        ClassPathResource classPathResource = new ClassPathResource("meta/sqllite.yaml")
        new GQueryTemplate(dataSource, new FileReader(classPathResource.getFile()), new YamlParser())
    }

}