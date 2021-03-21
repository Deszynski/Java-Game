package Game;

public interface Static {
	int BOARD_WIDTH = 358;
    int BOARD_HEIGHT = 350;
    int BORDER_RIGHT = 30;
    int BORDER_LEFT = 5;

    int GROUND = 290;// poziom podlogi
    int BOMB_HEIGHT = 5;//dlugosc pocisku wroga

    int GO_DOWN = 15; // przesuniecie wrogow w dol 15px
    int NUMBER_OF_ALIENS_TO_DESTROY = 24; // liczba zestrzeleñ do wygrania gry
    int CHANCE = 5;
    int DELAY = 17;
    
    int PLAYER_WIDTH = 15;
    int PLAYER_HEIGHT = 10;
    
    int ALIEN_HEIGHT = 12;
    int ALIEN_WIDTH = 12;
    int ALIEN_INIT_X = 150; // pozycja startowa wrogow
    int ALIEN_INIT_Y = 5; // pozycja startowa wrogow
}
