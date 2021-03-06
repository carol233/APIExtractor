/*
 * Copyright (C) 2015 The Pennsylvania State University and the University of Wisconsin
 * Systems and Internet Infrastructure Security Laboratory
 *
 * Author: Damien Octeau
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.psu.cse.siis.coal;

import edu.psu.cse.siis.coal.PropagationSolver;
import edu.psu.cse.siis.coal.Result;

/**
 * A result builder interface. A result builder describes how a result is built using the
 * propagation solver after the problem is solved.
 */
public interface ResultBuilder {

  /**
   * Builds a result from a solver after solution of the problem.
   * 
   * @param solver A propagation solver.
   * @return A result.
   */
  public Result buildResult(PropagationSolver solver);

}
