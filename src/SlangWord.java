public class SlangWord {
    private String slangWord;
    private String definition;

    public SlangWord(String slangWord, String definition) {
        this.slangWord = slangWord;
        this.definition = definition;
    }


    public String getSlangWord() {
        return slangWord;
    }

    public void setSlangWord(String slangWord) {
        this.slangWord = slangWord;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public String toString() {
        return slangWord + " - " + definition;
    }
}
