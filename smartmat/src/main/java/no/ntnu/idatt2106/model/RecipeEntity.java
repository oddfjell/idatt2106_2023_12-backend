package no.ntnu.idatt2106.model;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * RecipeEntity
 */
@Entity
@Table(name = "recipe")
public class RecipeEntity {

   /**
    * COLUMNS
    */
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String url;

   private int value;

   @Transient
   private String[] ingredients;

   private String title;

   private int servings;

   private String image;

   private final int SCRAPEDSERVING = 4;

   /**
    * equals method
    */
   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      RecipeEntity recipeEntity = (RecipeEntity) o;
      return Objects.equals(url, recipeEntity.url) && Arrays.equals(ingredients, recipeEntity.ingredients) && Objects.equals(title, recipeEntity.title);
   }

   /**
    * hashCode method
    * @return int
    */
   @Override
   public int hashCode() {
      int result = Objects.hash(url, title);
      result = 31 * result + Arrays.hashCode(ingredients);
      return result;
   }

   /**
    * CONSTRUCTORS
    */
   public RecipeEntity() {
   }

   public RecipeEntity(String url, String title, String[] ingredients, int servings, String image) {
      super();
      this.url = url;
      this.value = 0;
      this.ingredients = ingredients;
      this.title = title;
      this.servings = servings;
      this.image = image;
      changeServings();
   }

   /**
    * GETTERS
    */
   public Long getId() {
      return id;
   }

   public int getServings() {
      return servings;
   }

   public String getUrl() {
      return url;
   }

   public int getValue() {
      return value;
   }

   public String[] getIngredients() {
      return ingredients;
   }

   public String getTitle() {
      return title;
   }

   public String getImage() {
      return image;
   }

   /**
    * SETTERS
    */
   public void setId(Long id) {
      this.id = id;
   }

   public void setServings(int servings) {
      this.servings = servings;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public void setValue() {
      this.value++;
   }

   public void setIngredients(String[] ingredients) {
      this.ingredients = ingredients;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public void setImage(String image) {
      this.image = image;
   }

   /**
    * Method to change number of servings
    */
   private void changeServings() {
      for (int f = 0; f < this.ingredients.length; f++) {
         String i = this.ingredients[f];
         for (String s: i.replaceAll("[^\\d.]", " ")
                 .trim().replaceAll(" +", " ").split(" ")) {
            try {
               float j = Float.parseFloat(s);

               float k = j*this.servings/SCRAPEDSERVING;
               this.ingredients[f] = i.replace(s, String.valueOf(k));
               break;
            }
            catch (NumberFormatException e) {
            }
         }
      }
   }

   /**
    * toString method
    * @return String
    */
   @Override
   public String toString() {
      return "RecipeEntity{" +
              "url='" + url + '\'' +
              ", value=" + value +
              ", ingredients=" + Arrays.toString(ingredients) +
              ", title='" + title + '\'' +
              '}';
   }
}
