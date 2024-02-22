package tdd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Setter
    private Long id;
    private String firstName;
    private String secondName;
    private String address;
    private String sex;
    private String email;

    public Person(String firstName, String secondName, String address, String sex, String email) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.address = address;
        this.sex = sex;
        this.email = email;
    }
}