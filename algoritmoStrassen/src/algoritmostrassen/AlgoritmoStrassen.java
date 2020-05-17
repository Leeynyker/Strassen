/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmostrassen;

/**
 *
 * @author leeyn
 */
public class AlgoritmoStrassen {

    /**
     * @param args the command line arguments
     */
    private double matrizA[][];
    private double matrizB[][];
    private int fA;
    private int cA;
    private int fB;
    private int cB;
    public int N,Nn;
    public int temporal=0;//USAR ESTA VARIABLE PARA A ECUACION TEMPORAL
    public int contador=0;//USAR ESTA VARIABLE PARA EL CONTADOR

    public AlgoritmoStrassen(double[][] matrizA, double[][] matrizB, int fA, int cA, int fB, int cB) {
        this.matrizA = matrizA;
        this.matrizB = matrizB;
        this.fA = fA;
        this.cA = cA;
        this.fB = fB;
        this.cB = cB;
    }
    
    public double[][] generarMAtriz(){
        int nmA,nmB;
        double dosn;
        if(this.fA<this.cA){
            nmA=this.cA;
        }else{
            nmA=this.fA;
        }
        
        if(this.fB<this.cB){
            nmB=this.cB;
        }else{
            nmB=this.fB;
        }
        
        if(nmA>nmB){
            Nn=nmA;
        }else{
            Nn=nmB;
        }
        
        N=Nn;
        dosn=1.5;
        while((dosn%1)!=0){
            dosn=log(N,2);
            if((dosn%1)!=0){
                N++;
            }
        }
        double matrizA1[][] = new double[N][N];
        double matrizB1[][] = new double[N][N];
        
        for(int i =0;i<N;i++){
            for(int j=0;j<N;j++){
                if((i<this.fA) && (j<this.cA)){
                    matrizA1[i][j]=matrizA[i][j];
                }else{
                    matrizA1[i][j]=0;
                }
            }
        }

        
        for(int i =0;i<N;i++){
            for(int j=0;j<N;j++){
                if((i<this.fB) && (j<this.cB)){
                    matrizB1[i][j]=matrizB[i][j];
                }else{
                    matrizB1[i][j]=0;
                }
            }
        }
        
        
        
        double MatrizC[][]=multiplicar(matrizA1,matrizB1);
        
        return MatrizC;
    }
    
    public double[][] multiplicar(double[][]A,double[][]B){
        int t = A.length;  
        double[][] Resul = new double [t][t];
        
        if(t==1){
            Resul[0][0]=A[0][0]*B[0][0];
        }else{
            double[][] A11 = new double[t/2][t/2];
            double[][] A12 = new double[t/2][t/2];
            double[][] A21 = new double[t/2][t/2];
            double[][] A22 = new double[t/2][t/2];
            double[][] B11 = new double[t/2][t/2];
            double[][] B12 = new double[t/2][t/2];
            double[][] B21 = new double[t/2][t/2];
            double[][] B22 = new double[t/2][t/2];
            
            cpara(A, A11, 0 , 0);
            cpara(A, A12, 0 , t/2);
            cpara(A, A21, t/2, 0);
            cpara(A, A22, t/2, t/2);
            
            
            cpara(B, B11, 0 , 0);
            cpara(B, B12, 0 , t/2);
            cpara(B, B21, t/2, 0);
            cpara(B, B22, t/2, t/2);
            
            double [][] M1 = multiplicar(sumar(A11, A22), sumar(B11, B22));
            double [][] M2 = multiplicar(sumar(A21, A22), B11);
            double [][] M3 = multiplicar(A11, restar(B12, B22));
            double [][] M4 = multiplicar(A22, restar(B21, B11));
            double [][] M5 = multiplicar(sumar(A11, A12), B22);
            double [][] M6 = multiplicar(restar(A21, A11), sumar(B11, B12));
            double [][] M7 = multiplicar(restar(A12, A22), sumar(B21, B22));
            
            double [][] C11 = sumar(restar(sumar(M1, M4), M5), M7);
            double [][] C12 = sumar(M3, M5);
            double [][] C21 = sumar(M2, M4);
            double [][] C22 = sumar(restar(sumar(M1, M3), M2), M6);
            
            armar(C11, Resul, 0 , 0);
            armar(C12, Resul, 0 , t/2);
            armar(C21, Resul, t/2, 0);
            armar(C22, Resul, t/2, t/2);
        }  
        
        return Resul;
    }
    
    public void armar(double[][] C, double[][] P, int iB, int jB) 
    {
        for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                P[i2][j2] = C[i1][j1];
    }  
    
    public double[][] restar(double[][] A, double[][] B)
    {
        int n = A.length;
        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }
    
    
    public double[][] sumar(double[][] A, double[][] B)
    {
        int n = A.length;
        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }
    
    
    public void cpara(double[][] P, double[][] C, int iB, int jB) 
    {
        for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                C[i1][j1] = P[i2][j2];
    }
    
    private static Double log(double num, int base) {
      return (Math.log10(num) / Math.log10(base));
   }
    
    public static void main(String[] args) {
        LauncherUI user = new LauncherUI();
        user.setLocationRelativeTo(null);
        user.setVisible(true);        
    }

}
