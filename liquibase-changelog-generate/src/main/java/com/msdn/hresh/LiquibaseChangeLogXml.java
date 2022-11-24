package com.msdn.hresh;

import cn.hutool.core.util.StrUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/9/26 10:17 上午
 * @description
 */
@Mojo(name = "generateModelChangeXml", defaultPhase = LifecyclePhase.PACKAGE)
public class LiquibaseChangeLogXml extends AbstractMojo implements LiquibaseChangeLog {

  // 配置的是本maven插件的配置，在pom使用configration标签进行配置 property就是名字，
  // 在配置里面的标签名字。在调用该插件的时候会看到
  @Parameter(property = "sourceFolderPath")
  private String sourceFolderPath;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    System.out.println("Create a new empty model changelog in liquibase yaml file.");
    String userName = GitUtil.getGitUserName();

    String changeLogFileName = getChangeLogFileName(sourceFolderPath);
    if (StrUtil.isNotBlank(changeLogFileName)) {
      generateXmlChangeLog(changeLogFileName, userName);
    }
  }

  private void generateXmlChangeLog(String changeLogFileName, String userName) {
    String changeLogFileFullName = changeLogFileName + ".xml";
    File file = new File(sourceFolderPath, changeLogFileFullName);
    String content = "<?xml version=\"1.1\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
        + "<databaseChangeLog xmlns=\"http://www.liquibase.org/xml/ns/dbchangelog\"\n"
        + "  xmlns:ext=\"http://www.liquibase.org/xml/ns/dbchangelog-ext\"\n"
        + "  xmlns:pro=\"http://www.liquibase.org/xml/ns/pro\"\n"
        + "  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
        + "  xsi:schemaLocation=\"http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd http://www.liquibase.org/xml/ns/pro http://www.liquibase.org/xml/ns/pro/liquibase-pro-latest.xsd http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd\">\n"
        + "  <changeSet author=\" " + userName + "\" id=\"" + changeLogFileName + "\">\n"
        + "  </changeSet>\n"
        + "</databaseChangeLog>";
    try {
      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(content);
      bw.close();
      fw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
