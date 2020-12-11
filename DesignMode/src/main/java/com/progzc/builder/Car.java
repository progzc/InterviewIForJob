package com.progzc.builder;

/**
 * @Description 建造者模式的一种简化
 * @Author zhaochao
 * @Date 2020/12/11 15:40
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Car {
    private String carType;
    private int seats;
    private String engine;
    private String transmission;
    private String tripComputer;
    private String gpsNavigator;

    /* 禁止无参构造 */
    private Car(){
        throw new RuntimeException("Can't init...");
    }
    private Car(Builder builder){
        this.carType = builder.carType;
        this.seats = builder.seats;
        this.engine = builder.engine;
        this.transmission = builder.transmission;
        this.tripComputer = builder.tripComputer;
        this.gpsNavigator = builder.gpsNavigator;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carType='" + carType + '\'' +
                ", seats=" + seats +
                ", engine='" + engine + '\'' +
                ", transmission='" + transmission + '\'' +
                ", tripComputer='" + tripComputer + '\'' +
                ", gpsNavigator='" + gpsNavigator + '\'' +
                '}';
    }

    public static final class Builder{
        private String carType;
        private int seats;
        private String engine;
        private String transmission;
        private String tripComputer;
        private String gpsNavigator;

        public Builder(){ }
        public Builder setCarType(String carType){
            this.carType = carType;
            return this;
        }
        public Builder setSeats(int seats){
            this.seats = seats;
            return this;
        }
        public Builder setEngine(String engine){
            this.engine = engine;
            return this;
        }
        public Builder setTransmission(String transmission){
            this.transmission = transmission;
            return this;
        }
        public Builder setTripComputer(String tripComputer){
            this.tripComputer = tripComputer;
            return this;
        }
        public Builder setGpsNavigator(String gpsNavigator){
            this.gpsNavigator = gpsNavigator;
            return this;
        }
        public Car build(){
            return new Car(this);
        }
    }

    public static void main(String[] args) {
        Car car = new Car.Builder()
                .setCarType("SUV")
                .setSeats(6)
                .setEngine("1.6T")
                .setTransmission("混合动力")
                .setTripComputer("12.9寸液晶大屏")
                .setGpsNavigator("北斗")
                .build();
        System.out.println(car);
    }
}
