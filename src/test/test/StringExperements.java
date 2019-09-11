package test;

/**
 * Created by popov on 11.02.19.
 */
public class StringExperements {
    public static void main(String[] args) {
        StringExperements experements = new StringExperements();
        /*experiment 1*/
        System.out.println(experements.isPolindrome("Madam, I'm Adam!"));
    }

    private boolean isPolindrome(String sourceText) {
        String text1 = sourceText.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
        System.out.println(text1);
        StringBuilder text2 = new StringBuilder(text1).reverse();
        System.out.println(text2);
        return text2.toString().equals(text1);
    }
}

