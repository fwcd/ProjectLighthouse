package lighthouse.ai.layers;

import lighthouse.ai.WeightIterator;

public class Dense{

    private WeightIterator model;

    public Dense(WeightIterator model){
        this.model = model;
    }

    public double[][] calculate(double[][] in, int size){
        double[][] res = new double[size][];
        for (int i = 0; i < size; i++){
            res[i] = new double[1];
            for (int o = 0; 0 < in.length; o++){
                res[i][0] += in[o][0] * model.getNextWeight();
            }
            res[i][0] += model.getNextWeight();
        }
        return res;
    }
}