package no.ntnu.idatt2106.model;

import java.util.Arrays;

public class Recipe {

   private String url;
   private int value;
   private String[] ingredients;
   private String title;
   private int servings;

   public Recipe(String url, String title, String[] ingredients) {
      this.url = url;
      this.value = 0;
      this.ingredients = ingredients;
      this.title = title;
      this.servings = 4;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public int getValue() {
      return value;
   }

   public void setValue() {
      this.value++;
   }

   public String[] getIngredients() {
      return ingredients;
   }

   public void setIngredients(String[] ingredients) {
      this.ingredients = ingredients;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   @Override
   public String toString() {
      return "Recipe{" +
              "url='" + url + '\'' +
              ", value=" + value +
              ", ingredients=" + Arrays.toString(ingredients) +
              ", title='" + title + '\'' +
              '}';
   }
}
