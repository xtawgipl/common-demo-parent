package demo.common;

import demo.common.api.Driver;

import java.util.Iterator;
import java.util.ServiceLoader;

public class App {
    public static void main(String[] args) {

        ServiceLoader<Driver> loader = ServiceLoader.load(Driver.class);
        Iterator<Driver> iterator = loader.iterator();
        while (iterator.hasNext()){
            Driver driver = iterator.next();
            driver.connect("localhost", 80);
        }
    }
}
