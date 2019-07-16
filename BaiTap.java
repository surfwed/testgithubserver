import java.util.ArrayList;
import java.util.Scanner;

public class BaiTap {
	static final double INF = 1e15;
	static final double EPS = 1e-9;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		double y = sc.nextDouble();
		ArrayList<String> types = new ArrayList<>();
		ArrayList<ArrayList<Integer>> adj = new ArrayList();
		for (int i = 0; i < n; i++) {
			types.add(new String());
			adj.add(new ArrayList());
		}
		for (int i = 1; i < n; i++) {
			types.set(i, sc.next());
			if (types.get(i).equals("max0")) {
				adj.get(i).add(sc.nextInt());
			} else if (types.get(i).equals("sum")) {
				adj.get(i).add(sc.nextInt());
				adj.get(i).add(sc.nextInt());
			} else {
				int k = sc.nextInt();
				while (k-- > 0) {
					adj.get(i).add(sc.nextInt());
				}
			}
		}
		ArrayList<Double> f = new ArrayList();
		for (int i = 0; i < n; i++) {
			f.add(INF);
		}
		f.set(0, 1.0);
		double fmax = evaluate(adj, types, f, n - 1);
		for (int i = 0; i < n; i++) {
			f.set(i, INF);
		}
		f.set(0, -1.0);
		double fmin = evaluate(adj, types, f, n - 1);
		if (fmin - EPS <= y && y <= fmax + EPS) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}
	}

	private static double evaluate(ArrayList<ArrayList<Integer>> adj, ArrayList<String> types, ArrayList<Double> f,
			int u) {
		if (f.get(u) < INF) {
			return f.get(u);
		}
		if (types.get(u).equals("max0")) {
			f.set(u, Math.max(0.0, evaluate(adj, types, f, adj.get(u).get(0))));
		} else if (types.get(u).equals("sum")) {
			f.set(u, evaluate(adj, types, f, adj.get(u).get(0)) + evaluate(adj, types, f, adj.get(u).get(1)));
		} else if (types.get(u).equals("avg")) {
			double fu = 0.0;
			for (int v : adj.get(u)) {
				fu += evaluate(adj, types, f, v) / (adj.get(u).size());
			}
			f.set(u, fu);
		} else {
			double fu = -INF;
			for (int v : adj.get(u)) {
				fu = Math.max(fu, evaluate(adj, types, f, v));
			}
			f.set(u, fu);
		}
		return f.get(u);
	}
}
