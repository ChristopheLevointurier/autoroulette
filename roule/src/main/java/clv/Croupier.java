/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv;

import clv.Controller.EventController;
import clv.common.Utils;
import clv.sub.Roulette;
import clv.sub.Roulette.TypeRoulette;
import clv.sub.RouletteNumber;
import static clv.sub.RouletteNumber.RouletteColor.BLACK;
import static clv.sub.RouletteNumber.RouletteColor.GREEN;
import static clv.sub.RouletteNumber.RouletteColor.RED;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author CLV
 */
public class Croupier {

    private static List<AbstractPlayer> players = new ArrayList<>();
    private static List<AbstractPlayer> runningPlayers = new ArrayList<>();
    private static boolean running = false;
    private static int nbrSessionsStart = 2;
    private static int nbrSessions = 2;
    public static boolean wait = true;
    private static RouletteNumber number;

    public static boolean isWait() {
        return wait;
    }

    public static void setWait(boolean wait) {
        Croupier.wait = wait;
    }

    public static void doClick() {
        wait = false;
    }

    public static void newSessionGroup() {
        nbrSessions = nbrSessionsStart;
    }

    public static int getPlayerAmount() {
        return players.size();
    }

    public static int getActivePlayerAmount() {
        return runningPlayers.size();
    }

    public static void addPlayer(AbstractPlayer p) {
        players.add(p);
        runningPlayers.add(p);
    }

    public static void removePlayer(AbstractPlayer p) {
        players.remove(p);
        runningPlayers.remove(p);
    }

    public static void setRoulette(TypeRoulette t) {
        Roulette.setType(t);
    }

    public static void run() {
        waiting();
        do {
            running = true;
            runAllSessions();
            running = false;
        } while (!Casino.batchMode);
    }

    public static void runAllSessions() {
        nbrSessions = nbrSessionsStart;
        do {
            runOneSession();
            waiting();
            nbrSessions--;
        } while (nbrSessions > 0);
        EventController.broadcast(Utils.AppEvent.END_SESSION_GROUP);
    }

    private static void runOneSession() {
        runningPlayers.clear();
        for (AbstractPlayer p : players) {
            p.raz();
            runningPlayers.add(p);
        }
        do {
            for (AbstractPlayer p : runningPlayers) {
                p.bet();
            }
            EventController.broadcast(Utils.AppEvent.BET_DONE);
            number = Roulette.getNextNumber();
            EventController.broadcast(Utils.AppEvent.NEW_NUMBER);
            List<AbstractPlayer> deadPlayers = new ArrayList<>();

            for (AbstractPlayer p : runningPlayers) {
                pay(p, number);
                if (p.gameover()) {
                    deadPlayers.add(p);
                }
            }
            for (AbstractPlayer p : deadPlayers) {
                runningPlayers.remove(p);
            }
            waiting();
        } while (!runningPlayers.isEmpty()); //tant qu'il reste des joueurs    
        EventController.broadcast(Utils.AppEvent.END_SESSION);
    }

    private static void waiting() {
        if (!Casino.batchMode) {
            while (wait) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    System.out.println("next run");
                }
            }
            wait = true;
        }
    }

    public static int getNbrSessions() {
        return nbrSessions;
    }

    public static void setNbrSessionsStart(int nbrSessionsSt) {
        Croupier.nbrSessionsStart = nbrSessionsSt;
    }

    private static void pay(AbstractPlayer p, RouletteNumber number) {
        //couleurs
        switch (number.getCoul()) {
            case BLACK:
                p.addMoney(p.getMise().getNOIR() * 2);
            case GREEN: {
                //   switch (Roulette.getType()) {
                //      case EN:{
                p.addMoney(p.getMise().getROUGE() / 2 + p.getMise().getNOIR() / 2);
                //      }
                //  }
            }
            case RED:
                p.addMoney(p.getMise().getROUGE() * 2);
        }
        p.pushState(number);
    }

    public static RouletteNumber getNumber() {
        return number;
    }
    /**
     * ***
     *
     * Mise sur un numéro (plein) → 35 fois la mise Mise sur deux numéros
     * (cheval : exemple : 7-10) → 17 fois la mise Mise sur trois numéros
     * (transversale : exemple : 7-8-9) → 11 fois la mise Mise sur quatre
     * numéros (carré : exemple :25-26-28-29) → 8 fois la mise Mise sur six
     * numéros (sixain : exemple :13-14-15-16-17-18) → 5 fois la mise Mise sur
     * douze numéros (douzaine ou colonne : exemple : colonne 1-34 ou douzaine
     * 1-12) → 2 fois la mise Mise sur une chance simple, soit dix-huit numéros
     * (Noir-Rouge - Pair-Impair - Manque-Passe) → 1 fois la mise Mise sur
     * vingt-quatre numéros (deux colonnes adjacentes, vingt-quatre premiers
     * (1-24) ou vingt-quatre derniers (13-36), ces mises étant placées à cheval
     * sur les colonnes ou les douzaines) → la moitié de la mise Le zéro fait
     * perdre les mises engagées sur les chances multiples (c'est-à-dire toutes
     * les mises portant sur les numéros, du plein à la douzaine). Les mises
     * jouées sur les chances simples : à la roulette anglaise (la plus jouée
     * dans les casinos français) perdent la moitié de leur valeur (la banque en
     * garde la moitié) à la roulette française sont « emprisonnées » et le
     * tirage suivant détermine si le joueur récupère sa mise (si sa chance
     * simple est tirée), sans paiement, ou si elle est perdue. Si le tirage
     * suivant est un nouveau 0, il faudra deux tirages consécutifs de la chance
     * simple concernée pour que le joueur puisse récupérer sa mise et ainsi de
     * suite. à la roulette américaine sont perdues. Il en est d'ailleurs de
     * même pour le double zéro (00) Ces gains sont notés hors récupération de
     * la mise initiale. C'est-à-dire que, par exemple, pour une mise à « cheval
     * », on gagne 17 fois la mise + la mise initiale. Autre exemple : en misant
     * 2 € en jeu sur un numéro (miser en « plein »), et ce même numéro sort, 70
     * € sont gagnés et la mise de départ (2 €) est récupérée. On obtient donc
     * 72 € au total. Plus généralement, le gain est calculé de sorte que
     * l'espérance mathématique soit nulle pour le jeu avec 36 numéros ; l'ajout
     * du zéro permet de rendre le jeu favorable à la banque. Ce gain peut être
     * calculé par la formule suivante : (36 - n) / n où n est le nombre de
     * numéros gagnants pour la mise considérée (plein, cheval, ...).
     *
     */
}
