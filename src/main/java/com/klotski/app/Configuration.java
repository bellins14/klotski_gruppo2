package com.klotski.app;

public class Configuration {

    //configurazione scelta
    private final int _configuration;

    // Array di piece che rappresenta la configurazione
    // Saranno ordinati dal più grande al più piccolo, per comunicare più agevolmente con l'API BNM
    protected Piece[] blocks = new Piece[10];


    //==============
    // CONSTRUCTORS
    //==============
    //di default ho scelto la prima
    public Configuration(){
        this._configuration = 1;
        setBlocks(_configuration);
    }

    public Configuration(int configuration){
        this._configuration = configuration;
        setBlocks(_configuration);
    }

    public Piece[] getBlocks() {
        return blocks;
    }

    public void setBlocks(int c){
        int[] blockTypes = getBlocksType(c);
        Tuple[] positions = getPositions(c);


        for (int j = 0; j < blocks.length; j++) {
            int pieceType = blockTypes[j];
            blocks[j] = new Piece(pieceType, "piece"+pieceType+".png");
            blocks[j].setLayoutX(positions[j].getX());
            blocks[j].setLayoutY(positions[j].getY());

            // #### DEBUG ####
            //System.out.println("Setted block "+blocks[j]);
        }
    }

    public int[] getBlocksType(int c) {
        int[] types;

        // assegna ordine rettangoli per sezione (ordine decrescente)
        switch (c) {
            case 1, 4 -> types = new int[]{3, 1, 1, 1, 1, 2, 0, 0, 0, 0};
            case 2, 3 -> types = new int[]{3, 1, 1, 1, 2, 2, 0, 0, 0, 0};
            default -> {
                return null;
            }
        }

        return types;
    }

    //in base alla conf mi ritornano le posizioni su come mettere i vari blocchi
    public Tuple[] getPositions(int c) {
        int[] positionX;
        int[] positionY;

        // Le misure si riferiscono al pixel in angolo in alto a sinistra, dei blocchi rispettivi
        // scritti in getBlocks
        switch (c) {
            case 1 -> {
                positionY = new int[]{0, 0, 0, 200, 200, 400, 200, 200, 300, 300};
                positionX = new int[]{100, 0, 300, 0, 300, 100,  100, 200, 100, 200};
            }
            case 2 -> {
                positionY = new int[]{0, 100, 200, 100, 400, 400, 0, 0, 300, 300};
                positionX = new int[]{100, 0, 100, 300, 0, 200, 0, 300, 0, 300};
            }
            case 3 -> {
                positionY = new int[]{100, 0, 100, 200, 300, 400, 0, 0, 0, 300};
                positionX = new int[]{200, 0, 100, 0, 100, 200, 100, 200, 300, 300};
            }
            case 4 -> {
                positionY = new int[]{0, 0, 0, 200, 200, 200, 300, 300, 400, 400};
                positionX = new int[]{100, 0, 300, 0, 300, 100, 100, 200, 0, 300};
            }
            default -> {
                return null;
            }
        }

        Tuple[] positions = new Tuple[positionX.length];
        for (int i = 0; i < positionX.length; i++) {
            positions[i] = new Tuple(positionX[i], positionY[i]);
        }

        return positions;
    }

    @Override
    public String toString(){
        StringBuilder string  = new StringBuilder("{\n" +
                "    blocks: [ ");
        for(Piece block : this.blocks) {
            string.append(block.toString());
        }
        string.append("],\n" +
                "    boardSize: [5, 4],\n" +
                "    escapePoint: [3, 1],\n" +
                "}");
        return string.toString();
    }
}
