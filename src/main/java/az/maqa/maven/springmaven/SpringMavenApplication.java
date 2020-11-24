package az.maqa.maven.springmaven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringMavenApplication {

    @Autowired
    private JdbcTemplate jdbc;

    public static void main(String[] args) {
        SpringApplication.run(SpringMavenApplication.class, args);
        System.out.println("Hello World");
    }

    @PostConstruct
    public void initialize() {
        Car car = new Car("Vaz2107", "Lada");
        car.setId(1L);

        jdbc.update("CREATE TABLE CAR(ID NUMBER PRIMARY KEY, NAME VARCHAR(30), BRAND VARCHAR(30))");
        jdbc.update("INSERT INTO CAR VALUES(?,?,?)", 1, car.getName(), car.getBrand());
    }

}
