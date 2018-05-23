package lucene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.util.BytesRef;

/**
 * Class that will get all the terms in the index.
 *
 */
public class AllTerms {
    private Map<String,Integer> allTerms;
    Integer totalNoOfDocumentInIndex;
    IndexReader indexReader;

    public AllTerms() throws IOException
    {
        allTerms = new HashMap<>();
        indexReader = IndexOpener.GetIndexReader();
        totalNoOfDocumentInIndex = IndexOpener.TotalDocumentInIndex();
    }

    public void initAllTerms() throws IOException
    {
        int pos = 0;
        for (int docId = 0; docId < totalNoOfDocumentInIndex; docId++) {
            Terms vector = indexReader.getTermVector(docId, Configuration.FIELD_CONTENT);
            TermsEnum termsEnum = vector.iterator( null );
            BytesRef text = null;
            while ((text = termsEnum.next()) != null) {
                String term = text.utf8ToString();
                allTerms.put(term, pos++);
            }
        }

        //Update position
        pos = 0;
        for(Entry<String,Integer> s : allTerms.entrySet())
        {
            s.setValue(pos++);
        }
    }

    public Map<String, Integer> getAllTerms() {
        return allTerms;
    }
}