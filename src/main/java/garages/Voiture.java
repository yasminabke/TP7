package garages;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Représente une voiture qui peut être stationnée dans des garages.
 */
@RequiredArgsConstructor
@ToString
public class Voiture {

	@Getter
	@NonNull
	private final String immatriculation;
	@ToString.Exclude // On ne veut pas afficher les stationnements dans toString
	private final List<Stationnement> myStationnements = new LinkedList<>();

	/**
	 * Fait rentrer la voiture au garage
	 * Précondition : la voiture ne doit pas être déjà dans un garage
	 *
	 * @param g le garage où la voiture va stationner
	 * @throws IllegalStateException Si déjà dans un garage
	 */
	public void entreAuGarage(Garage g) throws IllegalStateException {
		// Et si la voiture est déjà dans un garage ?
		if (estDansUnGarage()) {
			throw new IllegalStateException("Un garage contient déjà cette voiture.");
		}
		Stationnement s = new Stationnement(this, g);
		myStationnements.add(s);
	}

	/**
	 * Fait sortir la voiture du garage
	 * Précondition : la voiture doit être dans un garage
	 *
	 * @throws IllegalStateException si la voiture n'est pas dans un garage
	 */
	public void sortDuGarage() throws IllegalStateException {
		// TODO: Implémenter cette méthode
		if (!estDansUnGarage()) {
            throw new IllegalStateException("La voiture n'est pas dans un garage !");
        }
        // On termine le dernier stationnement
        dernierStationnement().terminer();
    }
	// Trouver le dernier stationnement de la voiture
	private Stationnement dernierStationnement() {
        return myStationnements.get(myStationnements.size() - 1);
    }


	/**
	 * Calcule l'ensemble des garages visités par cette voiture
	 *
	 * @return l'ensemble des garages visités par cette voiture
	 */
	public Set<Garage> garagesVisites() {
		// TODO: Implémenter cette méthode
		Set<Garage> garages = new LinkedHashSet<>();
		for (Stationnement s : myStationnements) {
			garages.add(s.getGarageVisite());
		}
		return garages;
	}

	/**
	 * Détermine si la voiture est actuellement dans un garage
	 *
	 * @return vrai si la voiture est dans un garage, faux sinon
	 */
	public boolean estDansUnGarage() {
		// TODO: Implémenter cette méthode
		return !myStationnements.isEmpty() && dernierStationnement().estEnCours();
		// Vrai si il y a des stationnements et le dernier stationnement est en cours
	}

	/**
	 * Pour chaque garage visité, imprime le nom de ce garage suivi de la liste des
	 * stationnements dans ce garage
	 * <br>
	 * Exemple :
	 *
	 * <pre>
	 * Garage(name=Universite Champollion Albi):
	 * 		Stationnement{ entree=13/11/2024, sortie=13/11/2024 }
	 * Garage(name=ISIS Castres):
	 * 		Stationnement{ entree=13/11/2024, sortie=13/11/2024 }
	 * 		Stationnement{ entree=13/11/2024, en cours }
	 * </pre>
	 *
	 * @param out l'endroit où imprimer (ex: System.out pour imprimer dans la
	 *            console)
	 */

	public void imprimeStationnements(PrintStream out) {
		// TODO: Implémenter cette méthode
		Map<Garage, List<Stationnement>> stationnementsParGarage = new LinkedHashMap<>();
		for (Stationnement s : myStationnements) {
			Garage g = s.getGarageVisite();
			stationnementsParGarage.putIfAbsent(g, new ArrayList<>());
			stationnementsParGarage.get(g).add(s);
		}

		// Utiliser les méthodes toString() de Garage et Stationnement
		for (Garage g : stationnementsParGarage.keySet()) {
			out.println(g.toString() + ":");
			for (Stationnement s : stationnementsParGarage.get(g)) {
				out.println("\t" + s.toString());
			}
		}

	}

}
