package com.golapp.forestquest.staff

import com.golapp.forestquest.R
import com.golapp.forestquest.room.entities.Item

data class ItemStats(
    val compositeName: String,
    val elementName: String,
    val description: String,
    val icon: Int
) {
    fun toItem(playerId: Int) = Item(
        name = compositeName,
        itemType = elementName,
        description = description,
        ownerId = playerId,
        icon = icon
    )
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
                        description = "${randomElement.name} description",
                        icon = randomElement.icon
                    )
                } else ItemStats(
                    compositeName = this.name,
                    elementName = Basic.BasicArmor.name,
                    description = "${Basic.BasicArmor.name} description",
                    icon = Basic.BasicArmor.icon
                )
            }
            Weapon -> {
                if (isRare()) {
                    val randomElement = WeaponElement.entries.getRandom()
                    ItemStats(
                        compositeName = "$randomElement ${this.name}",
                        elementName = randomElement.name,
                        description = "attack +${randomElement.attack}",
                        icon = randomElement.icon
                    )
                } else ItemStats(
                    compositeName = this.name,
                    elementName = Basic.BasicWeapon.name,
                    description = "attack +1",
                    icon = Basic.BasicWeapon.icon
                )
            }
            Potion -> {
                val randomElement = PotionElement.entries.getRandom()
                ItemStats(
                    compositeName = "$randomElement ${this.name}",
                    description = "${randomElement.name} description",
                    elementName = randomElement.name,
                    icon = randomElement.icon
                )
            }
            Enchant -> {
                val randomElement = EnchantElement.entries.getRandom()
                ItemStats(
                    compositeName = "${this.name} $randomElement",
                    description = "${randomElement.name} description",
                    elementName = randomElement.name,
                    icon = randomElement.icon
                )
            }
        }
    }
}

enum class Basic(val icon: Int) {
    BasicArmor(R.drawable.armor_basic),
    BasicWeapon(R.drawable.sword_basic)
}

enum class WeaponElement(val icon: Int, val attack: Int) {
    Flame(R.drawable.sword_flame, 5),
    Cold(R.drawable.sword_frost, 3)
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