package rs21.pokedex;

import java.sql.Types;
import java.util.ArrayList;

public class PokemonDetails {
    Sprites sprites;
    String name;
    String id;
    String base_experience;
    String weight;
    String height;
    String order;
    ArrayList<Type> types;
    ArrayList<Abilities> abilities;
    ArrayList<HeldItems> held_items;

    public String getAllTypes() {
        String s = "";
        for (Type t : types) {
            s += t.type.name + " - " + t.slot + "\n";
        }
        if (s.equals(""))
            return "N/A";
        return s;
    }

    public String getAllAbilities() {
        String s = "";
        for (Abilities t : abilities) {
            s += t.ability.name + " - " + t.slot + "\n";
        }
        if (s.equals(""))
            return "N/A";
        return s;
    }

    public String getAllHeldItems() {
        String s = "";
        for (HeldItems t : held_items) {
            s += t.item.name + "\n";
        }
        if (s.equals(""))
            return "N/A";
        return s;
    }
}
