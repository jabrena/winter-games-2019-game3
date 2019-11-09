package org.jab.microservices.router;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class MyResponse2 {

    private final List<String> list;

    public void MyResponse2() {

    }
}
