package com.golapp.forestquest.staff

import android.util.Log
import com.golapp.forestquest.R
import com.golapp.forestquest.room.entities.Item

data class ItemStats(
    val compositeName: String,
    val elementName: String,
    val icon: Int
) {
    fun toItem(playerId: Int) = Item(
        name = compositeName,
        itemType = elementName,
        ownerId = playerId,
        icon = icon
    )
    companion object {
        const val Basic = "Basic"
    }
}

enum class ItemClass(private val dropChance: Int, private val rareElementChance: Int) {
    Armor(14, 15),
    Weapon(6, 15),
    Potion(30, 100),
    Enchant(16, 100);
    fun tryToGetIt(): ItemStats? {
        val chance = Math.random()*100
        return if (dropChance >= chance) this.getItemStats()
        else null
    }
    private fun isRare(): Boolean {
        val chance = Math.random() * 100
        return if (rareElementChance >= chance) true
        else false
    }

    private fun getItemStats(): ItemStats {
        return when(this) {
            Armor -> {
                if (isRare()) {
                    val randomElement = ArmorElement.entries.getRandom()
                    ItemStats(
                        compositeName = "$randomElement ${this.name}",
                        elementName = randomElement.name,
                        icon = randomElement.icon
                    )
                } else ItemStats(
                    compositeName = this.name,
                    elementName = ItemStats.Basic,
                    icon = R.drawable.armor_basic
                )
            }
            Weapon -> {
                if (isRare()) {
                    val randomElement = WeaponElement.entries.getRandom()
                    ItemStats(
                        compositeName = "$randomElement ${this.name}",
                        elementName = randomElement.name,
                        icon = randomElement.icon
                    )
                } else ItemStats(
                    compositeName = this.name,
                    elementName = ItemStats.Basic,
                    icon = R.drawable.sword_basic
                )
            }
            Potion -> {
                val randomElement = PotionElement.entries.getRandom()
                ItemStats(
                    compositeName = "$randomElement ${this.name}",
                    elementName = randomElement.name,
                    icon = randomElement.icon
                )
            }
            Enchant -> {
                val randomElement = EnchantElement.entries.getRandom()
                ItemStats(
                    compositeName = "${this.name} $randomElement",
                    elementName = randomElement.name,
                    icon = randomElement.icon
                )
            }
        }
    }
}


enum class WeaponElement(val icon: Int) {
    Flame(R.drawable.sword_flame),
    Cold(R.drawable.sword_frost)
}

enum class ArmorElement(val icon: Int) {
    Golden(R.drawable.armor_rare),
    Silver(R.drawable.armor_spirit)
}

enum class PotionElement(val icon: Int) {
    Mana(R.drawable.potion_mana),
    Health(R.drawable.potion_health)
}

enum class EnchantElement(val icon: Int) {
    Armor(R.drawable.book_armor),
    Weapon(R.drawable.book_weapon)
}