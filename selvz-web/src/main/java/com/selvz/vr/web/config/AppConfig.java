/**
 * 
 */
package com.selvz.vr.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author casam
 *
 */
@Configuration
@ImportResource({ "classpath*:/applicationContext-database.xml" })
@ComponentScan(basePackages = "com.selvz.vr.*")
public class AppConfig {

}
