/*
 * Chart.java
 * Copyright (C) 2004-2005, Bjorn Bringert (bringert@cs.chalmers.se)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package se.chalmers.cs.gf.parse.chart;

import java.util.*;

/**
 *  A parse chart.
 */
public class Chart {

        private int n;

        private Set<Edge>[][] active;

        private Set<Edge>[][] passive;

        public Chart(int n) {
                this.n = n;
                this.active = buildArray();
                this.passive = buildArray();
        }

        private Set<Edge>[][] buildArray() {
                // causes an unchecked cast warning. The cast is neccessary
                // since we cannot create arrays of parametrized types.
                // See section 7.3 of
                // http://java.sun.com/j2se/1.5/pdf/generics-tutorial.pdf
                // FIXME: array is almost twice as big as a it needs to be.
                //        could fix this with a non-square array
                Set<Edge>[][] arr = (Set<Edge>[][])new Set<?>[n+1][n+1];
                for (int i = 0; i <= n; i++)
                        for (int j = i; j <= n; j++)
                                arr[i][j] = new HashSet<Edge>();
                return arr;
        }

        /**
         *  @return true if the edge was not already in the set, i.e. if it was added.
         */
        public boolean add(Edge e, int i, int j) {
                checkPos(i,j);
                if (e.isActive()) {
                        return active[i][j].add(e);
                } else {
                        return passive[i][j].add(e);
                }
        }

        public boolean addPassiveEdges(Collection<? extends Edge> es, int i, int j) {
                checkPos(i,j);
                return passive[i][j].addAll(es);
        }

        /**
         *  Get all the active edges in the (i,j) set.
         *  Modifying th returned set modifies the chart.
         */
        public Set<Edge> getActive(int i, int j) {
                checkPos(i,j);
                return active[i][j];
        }

        /**
         *  Get all the passive edges in the (i,j) set.
         *  Modifying th returned set modifies the chart.
         */
        public Set<Edge> getPassive(int i, int j) {
                checkPos(i,j);
                return passive[i][j];
        }

        private void checkPos(int i, int j) {
                if (i > j || i < 0 || j < 0 || i > n || j > n)
                        throw new IllegalArgumentException(
                                "i = " + i + ", j = " + j + ", n = " + n);
        }

        /**
         *  Get the input length that this chart is for.
         */
        public int getSize() {
                return n;
        }

        /**
         *  Remove all active edges.
         */
        public void purgeActive() {
                for (int i = 0; i <= n; i++)
                        for (int j = i; j <= n; j++)
                                active[i][j].clear();
        }

        /**
         *  Counts the number of passive edges in the chart.
         */
        public int countPassiveEdges() {
                return countEdges(passive);
        }

        /**
         *  Counts the number of active edges in the chart.
         */
        public int countActiveEdges() {
                return countEdges(active);
        }

        private int countEdges(Set<Edge>[][] arr) {
                int c = 0;
                for (int i = 0; i <= n; i++)
                        for (int j = i; j <= n; j++)
                                c += arr[i][j].size();
                return c;
        }

        public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("Passive edges:\n");
                appendEdges(sb, passive);
                sb.append("Active edges:\n");
                appendEdges(sb, active);
                return sb.toString();
        }

        private void appendEdges(StringBuilder sb, Set<Edge>[][] arr) {
                for (int i = 0; i <= n; i++) {
                        for (int j = i; j <= n; j++) {
                                for (Edge e : arr[i][j]) {
                                        sb.append("(").append(i).append(",");
                                        sb.append(j).append("): ");
                                        sb.append(e).append("\n");
                                }
                        }
                }
        }
}
