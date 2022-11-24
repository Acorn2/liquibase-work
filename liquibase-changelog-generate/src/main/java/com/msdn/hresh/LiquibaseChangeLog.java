package com.msdn.hresh;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import java.io.File;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/9/27 2:29 下午
 * @description
 */
public interface LiquibaseChangeLog {

  default String getChangeLogFileName(String sourceFolderPath) {
    System.out.println("> Please enter the id of this changelog:");
    Scanner scanner = new Scanner(System.in);
    String changeId = scanner.nextLine();
    if (StrUtil.isBlank(changeId)) {
      return null;
    }

    String changeIdPattern = "^[a-z][a-z0-9_]*$";
    Pattern pattern = Pattern.compile(changeIdPattern);
    Matcher matcher = pattern.matcher(changeId);
    if (!matcher.find()) {
      System.out.println("changelog id should match " + changeIdPattern);
      return null;
    }

    if (isExistedChangeId(changeId, sourceFolderPath)) {
      System.out.println("Duplicate changelog id :" + changeId);
      return null;
    }

    Date now = new Date();
    String timestamp = DateUtil.format(now, "yyyyMMdd_HHmmss_SSS");
    return timestamp + "__" + changeId;
  }

  default boolean isExistedChangeId(String changeId, String sourceFolderPath) {
    File file = new File(sourceFolderPath);
    File[] files = file.listFiles();
    if (null == files) {
      return false;
    }

    for (File f : files) {
      if (f.isFile()) {
        if (f.getName().contains(changeId)) {
          return true;
        }
      }
    }
    return false;
  }
}
