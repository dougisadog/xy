package corelib.multi

class EnvironmentItem {
    var settingLabel: String
    var settingName: String
    var settingItemKeys: MutableList<String> = mutableListOf()
    var settingItems: LinkedHashMap<String, String> = linkedMapOf()

    constructor(label: String, name: String, items: LinkedHashMap<String, String>) {
        settingLabel = label
        settingName = name
        settingItems = items
        items.forEach { (key, value) ->
            settingItemKeys.add(key)
        }
    }
}