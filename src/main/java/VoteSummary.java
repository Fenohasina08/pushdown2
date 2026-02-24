public class VoteSummary {
    private long validCount;
    private long blankCount;
    private long nullCount;

    public VoteSummary(long validCount, long blankCount, long nullCount) {
        this.validCount = validCount;
        this.blankCount = blankCount;
        this.nullCount = nullCount;
    }

    public long getValidCount() {
        return validCount;
    }

    public void setValidCount(long validCount) {
        this.validCount = validCount;
    }

    public long getBlankCount() {
        return blankCount;
    }

    public void setBlankCount(long blankCount) {
        this.blankCount = blankCount;
    }

    public long getNullCount() {
        return nullCount;
    }

    public void setNullCount(long nullCount) {
        this.nullCount = nullCount;
    }

    @Override
    public String toString() {
        return "VoteSummary(validCount=" + validCount + ", blankCount=" + blankCount + ", nullCount=" + nullCount + ")";
    }
}
