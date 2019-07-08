public class NeuronNetwork {

    private int numberLayer;
    private double learningRate;
    public NeuronNetwork(int numberLayer, double learningRate) throws ExceptionInInitializerError { // минимум 3 слоя
        if (numberLayer > 5 || numberLayer < 3) throw new ExceptionInInitializerError("Out of bounds number of layers");
        this.numberLayer = numberLayer;
        this.learningRate = learningRate;
    }

    private double[] inputNeurons;
    private double[] outputNeurons;
    private double[] hiddenNeurons1;
    private double[] hiddenNeurons2;
    private double[] hiddenNeurons3;
    public void setupNumberNeurons(int layer1, int layer2, int layer3) { // установка кол-ва нейронов на каждом слое
        inputNeurons = new double[layer1];
        hiddenNeurons1 = new double[layer2];
        outputNeurons = new double[layer3];
        initWeights();
    }
    public void setupNumberNeurons(int layer1, int layer2, int layer3, int layer4) {
        inputNeurons = new double[layer1];
        hiddenNeurons1 = new double[layer2];
        hiddenNeurons2 = new double[layer3];
        outputNeurons = new double[layer4];
        initWeights();
    }
    public void setupNumberNeurons(int layer1, int layer2, int layer3, int layer4, int layer5) {
        inputNeurons = new double[layer1];
        hiddenNeurons1 = new double[layer2];
        hiddenNeurons2 = new double[layer3];
        hiddenNeurons3 = new double[layer4];
        outputNeurons = new double[layer5];
        initWeights();
    }

    private double[][] weightsItoH1;
    private double[][] weightsH1toH2;
    private double[][] weightsH2toH3;
    private double[][] weightsH1toO;
    private double[][] weightsH2toO;
    private double[][] weightsH3toO;
    private void initWeights() { // инициализация весов
        weightsItoH1 = new double[inputNeurons.length][hiddenNeurons1.length];
        for(int i = 0; i < inputNeurons.length; i++) {
            for(int j = 0; j < hiddenNeurons1.length; j++) {
                weightsItoH1[i][j] = Math.random() - Math.random();
                //System.out.println("1layer: " + weightsItoH1[i][j]);
            }
        }
        if (numberLayer == 3) {
            weightsH1toO = new double[hiddenNeurons1.length][outputNeurons.length];
            for(int i = 0; i < hiddenNeurons1.length; i++) {
                for(int j = 0; j < outputNeurons.length; j++) {
                    weightsH1toO[i][j] = Math.random() - Math.random();
                    //System.out.println("2layer: " + weightsH1toO[i][j]);
                }
            }
        } else if (numberLayer == 4) {
            weightsH1toH2 = new double[hiddenNeurons1.length][hiddenNeurons2.length];
            weightsH2toO = new double[hiddenNeurons2.length][outputNeurons.length];
            for(int i = 0; i < hiddenNeurons1.length; i++) {
                for(int j = 0; j < hiddenNeurons2.length; j++) {
                    weightsH1toH2[i][j] = Math.random() - Math.random();
                }
            }
            for(int i = 0; i < hiddenNeurons2.length; i++) {
                for(int j = 0; j < outputNeurons.length; j++) {
                    weightsH2toO[i][j] = Math.random() - Math.random();
                }
            }
        } else if (numberLayer == 5) {
            weightsH1toH2 = new double[hiddenNeurons1.length][hiddenNeurons2.length];
            weightsH2toH3 = new double[hiddenNeurons2.length][hiddenNeurons3.length];
            weightsH3toO = new double[hiddenNeurons3.length][outputNeurons.length];
            for(int i = 0; i < hiddenNeurons1.length; i++) {
                for(int j = 0; j < hiddenNeurons2.length; j++) {
                    weightsH1toH2[i][j] = Math.random() - Math.random();
                }
            }
            for(int i = 0; i < hiddenNeurons2.length; i++) {
                for(int j = 0; j < hiddenNeurons3.length; j++) {
                    weightsH2toH3[i][j] = Math.random() - Math.random();
                }
            }
            for(int i = 0; i < hiddenNeurons3.length; i++) {
                for(int j = 0; j < outputNeurons.length; j++) {
                    weightsH3toO[i][j] = Math.random() - Math.random();
                }
            }
        }
    }

    private void saveWeights() {}
    private void loadWeights() {}

    public double[] countOutput(double[] inputData) {
        inputNeurons = inputData;
        for(int i = 0; i < hiddenNeurons1.length; i++) {
            hiddenNeurons1[i] = 0;
            for(int j = 0; j < inputNeurons.length; j++) {
                hiddenNeurons1[i] += inputNeurons[j]*weightsItoH1[j][i];
            }
            hiddenNeurons1[i] = activationFunction(hiddenNeurons1[i]);
        }
        if(numberLayer == 3) {
            for(int i = 0; i < outputNeurons.length; i++) {
                outputNeurons[i] = 0;
                for(int j = 0; j < hiddenNeurons1.length; j++) {
                    outputNeurons[i] += hiddenNeurons1[j]*weightsH1toO[j][i];
                }
                outputNeurons[i] = activationFunction(outputNeurons[i]);
            }
            return outputNeurons;
        } else if (numberLayer == 4) {
            for(int i = 0; i < hiddenNeurons2.length; i++) {
                hiddenNeurons2[i] = 0;
                for(int j = 0; j < hiddenNeurons1.length; j++) {
                    hiddenNeurons2[i] += hiddenNeurons1[j]*weightsH1toH2[j][i];
                }
                hiddenNeurons2[i] = activationFunction(hiddenNeurons2[i]);
            }
            for(int i = 0; i < outputNeurons.length; i++) {
                outputNeurons[i] = 0;
                for(int j = 0; j < hiddenNeurons2.length; j++) {
                    outputNeurons[i] += hiddenNeurons2[j]*weightsH2toO[j][i];
                }
                outputNeurons[i] = activationFunction(outputNeurons[i]);
            }
            return outputNeurons;
        } else if (numberLayer == 5) {
            for(int i = 0; i < hiddenNeurons2.length; i++) {
                hiddenNeurons2[i] = 0;
                for(int j = 0; j < hiddenNeurons1.length; j++) {
                    hiddenNeurons2[i] += hiddenNeurons1[j]*weightsH1toH2[j][i];
                }
                hiddenNeurons2[i] = activationFunction(hiddenNeurons2[i]);
            }
            for(int i = 0; i < hiddenNeurons3.length; i++) {
                hiddenNeurons3[i] = 0;
                for(int j = 0; j < hiddenNeurons2.length; j++) {
                    hiddenNeurons3[i] += hiddenNeurons2[j]*weightsH2toH3[j][i];
                }
                hiddenNeurons3[i] = activationFunction(hiddenNeurons3[i]);
            }
            for(int i = 0; i < outputNeurons.length; i++) {
                outputNeurons[i] = 0;
                for(int j = 0; j < hiddenNeurons3.length; j++) {
                    outputNeurons[i] += hiddenNeurons3[j]*weightsH3toO[j][i];
                }
                outputNeurons[i] = activationFunction(outputNeurons[i]);
            }
            return outputNeurons;
        } else return outputNeurons;
    }
    public void study(double[] trainData, double[] answears) {

    }
    private double activationFunction(double sum) {
        return 1/(1 + Math.exp(-sum));
    }
}
