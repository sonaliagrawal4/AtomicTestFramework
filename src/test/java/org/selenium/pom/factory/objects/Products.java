package org.selenium.pom.factory.objects;

import org.selenium.pom.factory.utils.JaksonUtils;

import java.io.IOException;

public class Products {

    public Products(){}

    public Products(int id) throws IOException {
      Products[] products=  JaksonUtils.deserializeJson("products.json", Products[].class );
      for(Products products1:products)
      {
          if(products1.getId() ==id)
          {
              this.id=id;
              this.name= products1.getName();
          }
      }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int id;
     private String name;
}
