-- Inicio carga de partidas
DELETE FROM BGN.PLAYPLAYERREL;
DELETE FROM BGN.PLAY;
DELETE FROM BGN.GAME WHERE OWNED = 0;
ALTER TABLE BGN.PLAY AUTO_INCREMENT = 1;
-- Fin carga de partidas


SELECT * FROM BGN.CHALLENGE;