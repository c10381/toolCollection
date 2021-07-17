# 增加Spring boot profile
此專案使用maven的方式來管理profiles
1. 在`pom.xml`中新增
   - `<build>`下
     ```xml=
        <resources>
          <resource>
            <directory>src/main/resources</directory>
            <excludes>
                <exclude>application*.yml</exclude>
            </excludes>
          </resource>
          <resource>
            <directory>src/main/resources</directory>
            <filtering>true</filtering>
            <includes>
              <include>application.yml</include>
              <include>application-${profile.active}.yml</include>
            </includes>
          </resource>
        </resources>
        <!-- FOR TEST -->
        <testResources>
          <testResource>
            <directory>src/test/resources</directory>
            <excludes>
              <exclude>application*.yml</exclude>
            </excludes>
          </testResource>
          <testResource>
            <directory>src/test/resources</directory>
            <filtering>true</filtering>
            <includes>
              <include>application.yml</include>
              <include>application-test.yml</include>
            </includes>
          </testResource>
        </testResources>
     ```

- `<build>`下
    ```xml=
       <profiles>
         <profile>
           <id>dev</id>
           <activation>
             <activeByDefault>true</activeByDefault>
           </activation>
           <properties>
             <profile.active>dev</profile.active>
           </properties>
         </profile>
         <profile>
           <id>test</id>
           <properties>
             <profile.active>test</profile.active>
           </properties>
         </profile>
         <profile>
           <id>prod</id>
           <properties>
             <profile.active>prod</profile.active>
           </properties>
         </profile>
       </profiles>
    ```
2. 新增各環境下application-*.yml
3. 在application.yml新增
    ```yml
    spring:
      profiles:
        active: @profile.active@
    ```
   
## Test相關
之後需要在每個Test中新增
`@ActiveProfiles("test")`

## Maven指定profile
`mvn package -P prod`