public final class Network {

    public String ip;
    public int Cclass = 256;

    public Network(String ip, int subnets) {

        System.out.println("IP SUBNET " + ip + "0/" + calcMask(subnets));
        System.out.println("IP SUBNET " + ip + divideClass(subnets, Cclass) + "/" + calcMask(subnets));

        int ipn = divideClass(subnets, Cclass);
        System.out.println("IPN = " + ipn);

        getSubnet(ipn, subnets, ip);

    }

    public int divideClass(int subnets, int Cclass) {
        int ipn = Cclass / subnets; //256/8 = 64
        return ipn;
    }

    public void getSubnet(int ipn, int subnets, String ip) {
        int ipw = 0;
        for (int i = 0; i < subnets; i++) {
            ipw = ipn + divideClass(subnets, Cclass);
            ipn = ipw;
            if (ipn > 256) {
                break;
            } else {
                System.out.println("IP SUBNET " + ip + ipn + "/" + calcMask(subnets));
            }
        }
    }

    public int calcMask(int subnets) {
        int maskValue = 32 - subnets;
        return maskValue;
    }

    public static void main(String[] args) {
        Network network = new Network("192.168.1.", 8);
    }

}