package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class BurgerTest {
    private Burger burger;

    @Mock
    private Bun bun;

    @Mock
    private Ingredient ingredientOne;

    @Mock
    private Ingredient ingredientTwo;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();

        when(bun.getName()).thenReturn("Булка1");
        when(bun.getPrice()).thenReturn(11.0f);

        when(ingredientOne.getName()).thenReturn("Салат");
        when(ingredientOne.getPrice()).thenReturn(35.0f);
        when(ingredientOne.getType()).thenReturn(IngredientType.FILLING);

        when(ingredientTwo.getName()).thenReturn("Сырный");
        when(ingredientTwo.getPrice()).thenReturn(23.0f);
        when(ingredientTwo.getType()).thenReturn(IngredientType.SAUCE);
    }

    @Test
    public void setBuns_ShouldSetBun() {
        burger.setBuns(bun);
        assertEquals(bun, burger.bun);
    }

    @Test
    public void addIngredient_ShouldAddIngredient() {
        burger.addIngredient(ingredientOne);
        assertEquals(1, burger.ingredients.size());
        assertEquals(ingredientOne, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredient_ShouldRemoveIngredient() {
        burger.addIngredient(ingredientOne);
        burger.addIngredient(ingredientTwo);

        burger.removeIngredient(0);
        assertEquals(1, burger.ingredients.size());
        assertEquals(ingredientTwo, burger.ingredients.get(0));
    }

    @Test
    public void moveIngredient_ShouldMoveIngredient() {
        burger.addIngredient(ingredientOne);
        burger.addIngredient(ingredientTwo);

        burger.moveIngredient(1, 0);
        assertEquals(ingredientTwo, burger.ingredients.get(0));
        assertEquals(ingredientOne, burger.ingredients.get(1));
    }

    @Test
    public void getReceipt_ShouldReturnNotNull() {
        burger.setBuns(bun);
        burger.addIngredient(ingredientOne);

        String receipt = burger.getReceipt();
        assertNotNull(receipt);
        assertFalse(receipt.isEmpty());
    }

    @RunWith(Parameterized.class)
    public static class PriceParameterizedTest {

        private float bunPrice;
        private Ingredient[] ingredients;
        private float expectedPrice;

        public PriceParameterizedTest(float bunPrice, Ingredient[] ingredients, float expectedPrice) {
            this.bunPrice = bunPrice;
            this.ingredients = ingredients;
            this.expectedPrice = expectedPrice;
        }

        @Parameterized.Parameters
        public static Collection<Object[]> GetPrice() {
            return Arrays.asList(new Object[][]{
                    {10.0f, new Ingredient[]{new Ingredient(IngredientType.FILLING, "Салат", 50.0f)}, 70.0f},
                    {10.0f, new Ingredient[]{
                            new Ingredient(IngredientType.SAUCE, "Сырный", 20.0f)
                    }, 90.0f}
            });
        }

        @Test
        public void testGetPrice() {
            Burger testBurger = new Burger();
            Bun bun = new Bun("Булка1", bunPrice);
            testBurger.setBuns(bun);

            for (Ingredient ingredient : ingredients) {
                testBurger.addIngredient(ingredient);
            }

            assertEquals(expectedPrice, testBurger.getPrice(), 0.001f);
        }
    }
}