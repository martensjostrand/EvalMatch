package evalmatch

import evalmatch.domain._

class PairGenerator{

  def createPairs(applications: List[Application]) = { 
    var index = 0;
    def nextIndex() = { 
      val currentIndex = index
      index = index + 1
      currentIndex
    }
    
    def createPairsFromApplication(application: Application) = { 
      val receiver = application.receiver;
      for(writerInfo <- application.writerInfos)
         yield EvaluationPair(writerInfo.writer, receiver, 
			      writerInfo.importance, nextIndex)
    }
    
    applications.distinct.foldLeft(List[EvaluationPair]()){ 
      (pairs, application) => {
	pairs ::: createPairsFromApplication(application);
      }
    }
  }
}
