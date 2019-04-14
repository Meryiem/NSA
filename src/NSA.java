
import java.util.*;
import java.io.*;

class NSA {

    int DectOneParExemple= 1;
    int DetecteAleatoire=2 ;

    int Detecteur=DetecteAleatoire;

    static float maxAnormal=-1,maxTP=-1;
    float TP=1, FN=2,TN=3,FP=4,DR=5,FAR=6;
    static int L = 10;
    
    int R = 5;

    int nbEx = 0;
    int OneD = 1;
    int aleatoireD = 2;

    int Ordre = aleatoireD;//////////////////////////////////////////

    int AllnbEx = 0;
    String fichier = "donnnees.txt";

    int nbR1 = 1000;

    int nbR0 = 10;
    R0 r0Tab[];
    Self selfTab[];
    Self AllselfTab[];
    float ValMin[], ValMax[];
    Vector<R0> R0_NonSelf ;
    Vector<Self> SelfVector;
    Vector<Self> AllSelfVector ;
    Vector<Self> SelfVectorNormal;
    Vector<Self> SelfVectorAnormal ;

    int normal, anormal;

     void Formules(){
         
           TP = (float)((float)SelfVectorAnormal.size() / (float)anormal);

            FN = (float) ((float)((float)SelfVectorNormal.size() - (float)normal)/(float)anormal) ;




            //TN= ;

             if(TN>1)TN=1;



            //FP= ;




            //DR=;

            //FAR= ;

            

         





            System.out.println("TP(true positive) = " + TP);
            System.out.println("FN (false negative) = " + FN);
          //   System.out.println("FP  = " + FP);
            //   System.out.println("TN  = " + TN);
             //    System.out.println("DR  = " + DR);
              //     System.out.println("FAR  = " + FAR);
     }





    void ChargerDonnees() {

AllSelfVector = new Vector<Self>();
        SelfVector = new Vector<Self>();
        ValMin = new float[L];
        ValMax = new float[L];

        for (int i = 0; i < L; i++) {
            ValMin[i] = 0;
            ValMax[i] = 0;
        }

        // ouverture du fichier
        BufferedReader IN = null;
        try {
            IN = new BufferedReader(new FileReader(fichier));
        } catch (Exception e) {
            System.err.println("Erreur : " + e);
        }
        int k = 0;
        String ligne = null;

        // gestion des éventuelles erreurs
        try {
            //   ligne = IN.readLine();
            //  int i = 0;
            // nbEx = Integer.parseInt(ligne);
            //this.entrer = new Entrer[nbEx];
            // ligne = IN.readLine();
            // nbEx = Integer.parseInt(ligne);
            //
            while ((ligne = IN.readLine()) != null) {

                String[] meriem = ligne.split(",");


                Self self = new Self();
                for (int i = 0; i < L; i++) {
                    self.donneesfloat[i] = Float.parseFloat(meriem[i]);
                }

                for (int i = 0; i < L; i++) {

                    if (self.donneesfloat[i] < ValMin[i]) {
                        ValMin[i] = self.donneesfloat[i];
                    }

                    if (self.donneesfloat[i] > ValMax[i]) {
                        ValMax[i] = self.donneesfloat[i];
                    }
                }
                if (meriem[meriem.length - 1].equals("normal")) {
                    SelfVector.addElement(self);
                    nbEx++;
                    normal++;
                }
                else anormal++;
                AllSelfVector.addElement(self);
                AllnbEx++;
                k++;
            }// fin while
            
          //  System.out.println("AllnbEx = " + AllnbEx);
            //System.out.println("nbEx = " + nbEx);


        } catch (Exception e) {
            System.err.println("Erreur : " + e);
        }
    }

    void MinMaxNormaliserDonnees() {

        /*
         *
         * normalisée = (originale - MIN) * (max - min) / (MAX - MIN) + min

        [MIN,MAX] : interval d'origine
        [min,max] : interval cible
        originale : valeur dans l'interval d'origine
        normalisée : valeur normalisée dans l'interval cible
         *
         *
         */
AllselfTab = new Self[AllnbEx];
            selfTab = new Self[nbEx];
        for (int i = 0; i < AllnbEx; i++) {
            Self self = AllSelfVector.elementAt(i);
            for (int j = 0; j < L; j++) {

                float result = (self.donneesfloat[j] - ValMin[j]) / (ValMax[j] - ValMin[j]);

                AllselfTab[i] = self;
                if (result >= 0.5) {
                    AllselfTab[i].donnees[j] = 1;
                } else {
                    AllselfTab[i].donnees[j] = 0;
                }
                //System.out.print( selfTab[i].donnees[j]+" , ");

            }

//System.out.println();
        }
       // System.out.println("AllselfTab.size = " + AllselfTab.length);
        for (int i = 0; i < nbEx; i++) {
            Self self = SelfVector.elementAt(i);
            for (int j = 0; j < L; j++) {

                float result = (self.donneesfloat[j] - ValMin[j]) / (ValMax[j] - ValMin[j]);

                selfTab[i] = self;
                if (result >= 0.5) {
                    selfTab[i].donnees[j] = 1;
                } else {
                    selfTab[i].donnees[j] = 0;
                }
                //System.out.print( selfTab[i].donnees[j]+" , ");

            }


        }
     //   System.out.println("selfTab.size = " + selfTab.length);
        SelfVector = null;
    }

    void GenererDectecteur() {
        R0_NonSelf = new Vector<R0>();
        for (int i = 0; i < nbEx; i++) {
            Self self = selfTab[i];
            for (int j = 0; j < nbR0; j++) {
                R0 r0 = r0Tab[j];
                boolean tr = true;
                int pp = 0;
                int nb = 0;
                while (tr) {
                    for (int k = pp; k < R + pp; k++) {
                        if (r0.R0[k] == self.donnees[k]) {
                        } else {
                            // for(int v=0;v<L;v++)
                            // System.out.print( r0.R0[v]+"");
                            //  System.out.println();
                            R0_NonSelf.addElement(r0);
                            tr = false;
                            break;
                        }
                    }
                    pp++;
                    if(Detecteur==DectOneParExemple){
                    if (tr == false) break;}
                    if (R + pp < L) {
                        break;
                    }
                }
                if (R0_NonSelf.size() == nbR1) {
                    break;
                }
            }

            if (R0_NonSelf.size() == nbR1) {
                break;
            }
        }
//System.out.println("R0_NonSelf.size = "+R0_NonSelf.size());
    }

    void CreerRandomStrings() {
        r0Tab = new R0[nbR0];
        for (int i = 0; i < nbR0; i++) {
            R0 r0 = new R0();
            r0Tab[i] = r0;

        }
    }

    void AppariementAleatoir() {
 SelfVectorNormal = new Vector<Self>();
  SelfVectorAnormal = new Vector<Self>();
        R0 r0;
        Random r = new Random();
        int index = r.nextInt(R0_NonSelf.size());
        r0 = R0_NonSelf.elementAt(index);
        for (int i = 0; i < AllnbEx; i++) {
            if (Ordre == aleatoireD) {

                index = r.nextInt(R0_NonSelf.size());
                r0 = R0_NonSelf.elementAt(index);
            }
            //Self self=AllselfTab[i];
            // for(int j=0;j<R0_NonSelf.size();j++){
            //R0 r0=R0_NonSelf.elementAt(j);
            boolean tr = true;
            int pp = 0;

            for (int m=0;m+R< L;m++) {
                int nb = 0;
                for (int k = m; k < R + m; k++) {
                    if (r0.R0[k] == AllselfTab[i].donnees[k]) {
                        nb++;
                    } else {
                        // for(int v=0;v<L;v++)
                        // System.out.print( r0.R0[v]+"");
                        //  System.out.println();
                        SelfVectorNormal.addElement(AllselfTab[i]);
                        tr = false;
                        break;
                    }
                }
                if (nb == R) {
                    SelfVectorAnormal.addElement(AllselfTab[i]);
                    break;
                }
                if(tr==false) break;
            }
            // }

        }


      
        //System.out.println("SelfVectorNormal.size = " + SelfVectorNormal.size());
    }

  void Appariement() {

SelfVectorNormal = new Vector<Self>();
  SelfVectorAnormal = new Vector<Self>();
        for (int i = 0; i < AllnbEx; i++) {

            //Self self=AllselfTab[i];
            int nbAnormal=0;
            int nbNormal=0;
            for(int j=0;j<R0_NonSelf.size();j++){
            R0 r0=R0_NonSelf.elementAt(j);
            boolean tr = true;


            for (int m=0;m+R< L;m++) {
                int nb = 0;
                for (int k = m; k < R + m; k++) {
                    if (r0.R0[k] == AllselfTab[i].donnees[k]) {
                        nb++;
                    } else {
                        // for(int v=0;v<L;v++)
                        // System.out.print( r0.R0[v]+"");
                        //  System.out.println();

                        nbNormal++;
                        tr = false;
                        break;
                    }
                }
                if (nb == R) {

                    nbAnormal++;
                    break;
                }
                if(tr==false) break;
            }
             }

            if(nbAnormal>nbNormal){SelfVectorAnormal.addElement(AllselfTab[i]);}
 else SelfVectorNormal.addElement(AllselfTab[i]);
        }


        System.out.println("SelfVectorAnormal.size = " + SelfVectorAnormal.size());
        System.out.println("SelfVectorNormal.size = " + SelfVectorNormal.size());
    }


    public void Run() {

        int iteration=1;







        ChargerDonnees();

        MinMaxNormaliserDonnees();

       //  for(int i=0;i<iteration;i++){
              CreerRandomStrings();
        GenererDectecteur();

        AppariementAleatoir();
 //System.out.println("nsa.anormal = " + nsa.anormal+ ",  nsa.anormal ="+nsa.anormal );



Formules();
      







// System.out.printl("SelfVectorAnormal.size = " +  SelfVectorAnormal.size()+ "  ,  ");
 //System.out.print("TP(true positive) = " + TP+" , ");
          //  System.out.println("FN (false negative) = " + FN);
            if(maxAnormal<SelfVectorAnormal.size() ){
                maxAnormal=SelfVectorAnormal.size();
          //  }
            if(maxTP<TP)
            maxTP=TP;

}


        System.out.println ("nsa.maxAnormal = " + maxAnormal+" , nsa.maxTP = "+maxTP);




    }








    public static void main(String[] arg) {

        int iteration=100;


       
 
        NSA nsa = new NSA();



        nsa.ChargerDonnees();

        nsa.MinMaxNormaliserDonnees();
for(int i=0;i<iteration;i++){
       System.out.println("i = " + i);
              nsa.CreerRandomStrings();
        nsa.GenererDectecteur();

        nsa.AppariementAleatoir();
 //System.out.println("nsa.anormal = " + nsa.anormal+ ",  nsa.anormal ="+nsa.anormal );
       nsa.Formules();
 //System.out.print("SelfVectorAnormal.size = " +  nsa.SelfVectorAnormal.size()+ "  ,  ");
 //System.out.print("TP(true positive) = " + nsa.TP+" , ");
            //System.out.println("FN (false negative) = " + nsa.FN);
            if(nsa.maxAnormal<nsa.SelfVectorAnormal.size() ){
                nsa.maxAnormal=nsa.SelfVectorAnormal.size();
           
            if(nsa.maxTP<nsa.TP)
            NSA.maxTP=nsa.TP;
           }
}
 

        System.out.println (" , nsa.maxTP = "+NSA.maxTP);


        

    }

/*
   void GenererDectecteur() {
        R0_NonSelf = new Vector<R0>();
        for (int i = 0; i < nbEx; i++) {
            Self self = selfTab[i];
            for (int j = 0; j < nbR0; j++) {
                R0 r0 = r0Tab[j];
                boolean tr = true;
                int pp = 0;
                int nb = 0;
                while (tr) {
                    for (int k = pp; k < R + pp; k++) {
                        if (r0.R0[k] == self.donnees[k]) {
                        } else {
                            // for(int v=0;v<L;v++)
                            // System.out.print( r0.R0[v]+"");
                            //  System.out.println();
                            R0_NonSelf.addElement(r0);
                            tr = false;
                            break;
                        }
                    }
                    pp++;
                    if (R + pp < L) {
                        break;
                    }
                }
                if (R0_NonSelf.size() == nbR1) {
                    break;
                }
            }

            if (R0_NonSelf.size() == nbR1) {
                break;
            }
        }
//System.out.println("R0_NonSelf.size = "+R0_NonSelf.size());
    }




 **/
   
}
