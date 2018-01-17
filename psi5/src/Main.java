public class Main{

    private static double learningRate = 0.1;
    private static int nots = 5;		//liczba danych testujacych
    private static int noi = 4;         //liczba wejsc
    private static int nols = 10;	//liczba danych uczących
    private static int nof = 3;         //liczba kwiatow
    private static int ll = 1000;			//maksymalna ilosc epok uczenia
    private static int non = 200;       //liczba neuronow
    public static void main ( String[] args ) {

        int successCounter = 0;		//licznik prób uczenia zakończonych powodzeniem
        int failCounter = 0;	//licznik prób uczenia zakończonych niepowodzeniem

        while ( successCounter != 15 && failCounter != 100 ) {

            WTA[] kohonens = new WTA[non];
            for ( int i = 0; i < non; i++ )
                kohonens[i] = new WTA( noi );

            int ages = learn( kohonens );

            if ( ages != ll ) {
                successCounter++;

                int winner;
                System.out.println( "Nr"+successCounter );
                System.out.println( "PO UCZENIU" );
                for ( int i = 0; i < nof; i++ ) {
                    winner = getWinner( kohonens, Flower.flowerLearn[i][0] );
                    System.out.println( "Flower[" + i + "] winner = " + winner );
                }
                System.out.println();

                System.out.println( "PO TESTOWANIU" );
                for ( int i = 0; i < nof; i++ ) {
                    for ( int j = 0; j < nots; j++ ) {
                        winner = getWinner( kohonens, Flower.flowerTest[i][j] );
                        System.out.println( "Flower[" + i + "][" + j + "] test winner = " + winner );
                    }
                    System.out.println();
                }
                System.out.println();


                System.out.println( "Ilość epok = " + ages + "\n\n\n" );
            }
            else failCounter++;
        }
        System.out.println( "\nIlość niepowodzeń = " + failCounter );
    }


    //uczenie sieci
    private static int learn ( WTA[] kohonens ) {

        int counter = 0;
        int winner;

        int[][] winners = new int[nof][nols];
        for ( int i = 0; i < nof; i++ )
            for ( int j = 0; j < nols; j++ )
                winners[i][j] = - 1;

        while ( ! isUnique( winners ) ) {


            for ( int i = 0; i < nof; i++ ) {
                for ( int j = 0; j < nols; j++ ) {
                    winner = getWinner( kohonens, Flower.flowerLearn[i][j] );
                    kohonens[winner].learn( Flower.flowerLearn[i][j], learningRate );
                }
            }

            for ( int i = 0; i < nof; i++ )
                for ( int j = 0; j < nols; j++ )
                    winners[i][j] = getWinner( kohonens, Flower.flowerLearn[i][j] );


            if ( ++ counter == ll )
                break;
        }

        return counter;
    }


    private static boolean isUnique ( int[][] winners ) {


        for ( int i = 0; i < nof; i++ )
            for ( int j = 1; j < nols; j++ )
                if ( winners[i][0] != winners[i][j] )
                    return false;


        for ( int i = 0; i < nof; i++ )
            for ( int j = 0; j < nof; j++ )
                if ( i != j )
                    if ( winners[i][0] == winners[j][0] )
                        return false;

        return true;
    }



    private static int getWinner ( WTA[] kohonens, double[] vector ) {

        int winner = 0;
        double minDistance = distanceBetweenVectors( kohonens[0].getW(), vector );


        for ( int i = 0; i < non; i++ ) {
            if ( distanceBetweenVectors( kohonens[i].getW(), vector ) < minDistance ) {
                winner = i;
                minDistance = distanceBetweenVectors( kohonens[i].getW(), vector );
            }
        }

        return winner;
    }



    public static double distanceBetweenVectors ( double[] vector1, double[] vector2 ) {

        double suma = 0.0;

        for ( int i = 0; i < vector1.length; i++ ) {

            suma += Math.abs( vector1[i] - vector2[i] );
        }

        return Math.sqrt( suma );
    }

}