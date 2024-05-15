package edu.ucan.bilheteira.user;

import edu.ucan.bilheteira.person.PersonEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserRegistrationRequest {
    private User user;
    private PersonEntity person;
    private List<UUID> roleIds;
}
