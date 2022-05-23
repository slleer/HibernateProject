import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "item")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "consumable")
    private boolean consumable;
    
    @Column(name = "attunementNeeded")
    private boolean attunement;

    @Column(name  = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CharacterItem> characterItemList = new ArrayList<CharacterItem>();

    public List<CharacterItem> getCharacterItemList() {
        return characterItemList;
    }

    public void setCharacterItemList(List<CharacterItem> characterItemList) {
        this.characterItemList = characterItemList;
    }

    public void addCharacterItem(CharacterItem charItem){
        this.characterItemList.add(charItem);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConsumable() {
        return consumable;
    }

    public void setConsumable(boolean consumable) {
        this.consumable = consumable;
    }

    public boolean isAttunement() {
        return attunement;
    }

    public void setAttunement(boolean attunement) {
        this.attunement = attunement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", consumable=" + consumable +
                ", attunement=" + attunement +
                ", description='" + description + '\'' +
                ", characterItemList=" + characterItemList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return consumable == item.consumable && attunement == item.attunement && id == item.id && name.equals(item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
