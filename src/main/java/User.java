import java.io.Serializable;
import java.math.BigDecimal;

public class User implements Serializable{
    private String name;
    private int age;
    private Integer salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public User(String name, int age, Integer salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public void setAge(int age) {

        this.age = age;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
