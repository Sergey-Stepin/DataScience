/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.core.data;

import java.util.List;

/**
 *
 * @author stepin
 */
public class InMemoryDataService <T extends DataType> implements DataService<T>{

    private final List<T> data;

    public InMemoryDataService(List<T> data) {
        this.data = data;
    }
    
    @Override
    public List<T> getAllData() {
        return data;
    }
    
}
