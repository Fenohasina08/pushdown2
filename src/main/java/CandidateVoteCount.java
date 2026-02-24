import java.util.Objects;

public class CandidateVoteCount {
    private String candidateName;
    private long validVoteCount;

    public CandidateVoteCount(String candidateName, long validVoteCount) {
        this.candidateName = candidateName;
        this.validVoteCount = validVoteCount;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public long getValidVoteCount() {
        return validVoteCount;
    }

    public void setValidVoteCount(long validVoteCount) {
        this.validVoteCount = validVoteCount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CandidateVoteCount that = (CandidateVoteCount) o;
        return validVoteCount == that.validVoteCount && Objects.equals(candidateName, that.candidateName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidateName, validVoteCount);
    }

    @Override
    public String toString() {
        return candidateName + "=" + validVoteCount;
    }

}
