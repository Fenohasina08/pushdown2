public class VoteTypeCount {
    private String voteType;
    private long count;

    public VoteTypeCount(String voteType, long count) {
        this.voteType = voteType;
        this.count = count;
    }

    public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "VoteTypeCount{" +
                "count=" + count +
                ", voteType='" + voteType + '\'' +
                '}';
    }
}
