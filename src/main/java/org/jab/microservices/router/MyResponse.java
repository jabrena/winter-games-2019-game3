package org.jab.microservices.router;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter(AccessLevel.NONE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyResponse {

    private boolean status;
}
