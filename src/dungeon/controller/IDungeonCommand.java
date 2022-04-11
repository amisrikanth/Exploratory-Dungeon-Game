package dungeon.controller;

import java.io.IOException;

import dungeon.model.IDungeon;

interface IDungeonCommand {

  String execute(IDungeon m) throws IOException;
}
