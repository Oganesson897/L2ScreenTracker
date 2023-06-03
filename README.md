# L2ScreenTracker

This mod allows one to open a menu within another menu,
by recording how to construct the previous menu before closing it.
This feature allows quick access to a lot of simple menu or backpacks.

Default implementation supports opening the following items in other menus:
- Crafting Table
- Smithing Table
- Grindstone
- Stone Cutter
- Cartography Table
- Loom

## Installation

Requirements:
- L2Serial
- L2Library

Compatibility:
- Curios
- L2Tabs

## Usage

### Menu Stacking
For a mod to open a menu on top of another one, it should call 
`ScreenTracker.onServerOpen` before opening the menu.

### Item Right Click
For a mod to listen to item right click in menu, it should
create an instance of `SlotClickHandler`, which automatically registers
itself. For easier use, one should either create
`ReadOnlyStackClickHandler`, which means the stack will not be modified,
or `WriteableStackClickHandler`, which allows click only when the container
supports the action through `PlayerSlot`.

Note that only unstackable items, and stackable items in 
`#l2screentracker:quick_access` with count of 1 can trigger
item right click.

### PlayerSlot and ItemSource
`PlayerSlot` is a way of tracking an ItemStack. It's a
reference of a slot in a container. The container should
be active, that means changes in the stack should not
be required to notify the container.

Currently it supports:
- Inventory
- Curios Slot
- Ender Chest Inventory
- Dimensional Backpack in L2Backpack

To add more support, register an `ItemSource`


