package pers.wxp.pattern.filter;

import java.util.List;

public interface Criteria {
    List<Person> meetCriteria(List<Person> persons);
}
