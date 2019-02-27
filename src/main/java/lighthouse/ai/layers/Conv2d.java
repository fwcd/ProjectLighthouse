package lighthouse.ai.layers;

import lighthouse.ai.WeightIterator;

public class Conv2d{

    private WeightIterator model;

    public Conv2d(WeightIterator model){
        this.model = model;
    }

    public double[][] calculate(double[][] in, int height, int width){
        double[][] res = new double[in.length - (height - 1)][];
        double bias = model.getNextWeight();
        for(int row = 0; row < res.length; row++){
            res[row] = new double[in[row].length - (width - 1)];
            for (int col = 0; col < res[row].length; col++){
                for (int i = 0 + row; i < height + row; i++){
                    for (int o = 0 + col; o < width + col; i++){
                        res[row][col] += in[i][o] * model.getNextWeight();
                    }
                }
                res[row][col] += bias;
            }
        }
        return res;
    }

}
