import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "character_items")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterItem {

    public CharacterItem(PlayerCharacter character, Item item) {
        this.character = character;
        this.item = item;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_id", referencedColumnName = "id")
    private PlayerCharacter character;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "i_id", referencedColumnName = "id")
    private Item item;

    @Column(name = "count")
    private int count;

    @Column(name = "attuned")
    private boolean isAttuned;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int decrementCount(int val) {
        if(this.getCount() - val < 0) {
            System.out.println("Can't remove " +
                    val + this.item.getName() +
                    "(s) bc only " + this.getCount() +
                    " exist!");
            return this.getCount();
        } else {
            return this.getCount() - val;
        }
    }
    public boolean isAttuned() {
        return isAttuned;
    }

    public void setAttuned(boolean attuned) {
        isAttuned = attuned;
    }

    @Override
    public String toString() {
        return "CharacterItem{" +
                "id='" + id + '\'' +
                ", character=" + character +
                ", item=" + item +
                ", count=" + count +
                ", isAttuned=" + isAttuned +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharacterItem)) return false;
        CharacterItem that = (CharacterItem) o;
        return id.equals(that.id) && character.equals(that.character) && item.equals(that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
