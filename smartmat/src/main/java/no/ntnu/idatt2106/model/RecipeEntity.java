package no.ntnu.idatt2106.model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "recipe")
public class RecipeEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String url;

   @Transient
   private int value;
   @Transient
   private String[] ingredients;
   @Transient
   private String title;
   @Transient
   private int servings;
   @Transient
   private String image;

   private final int SCRAPEDSERVING = 4;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      RecipeEntity recipeEntity = (RecipeEntity) o;
      return Objects.equals(url, recipeEntity.url) && Arrays.equals(ingredients, recipeEntity.ingredients) && Objects.equals(title, recipeEntity.title);
   }

   @Override
   public int hashCode() {
      int result = Objects.hash(url, title);
      result = 31 * result + Arrays.hashCode(ingredients);
      return result;
   }

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

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public int getServings() {
      return servings;
   }

   public void setServings(int servings) {
      this.servings = servings;
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

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }

   private void changeServings() {
      for (int f = 0; f < this.ingredients.length; f++) {
         String i = this.ingredients[f];
         for (String s: i.replaceAll("[^\\d.]", " ")
                 .trim().replaceAll(" +", " ").split(" ")) {
            System.err.println(s);
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
