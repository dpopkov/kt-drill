package learn.nc150;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class P0217ContainsDuplicateJDone implements P0217ContainsDuplicate {
    @Override
    public boolean containsDuplicate(@NotNull int[] nums) {
        Set<Integer> seenNumbers = new HashSet<>();
        for (int number : nums) {
            if (seenNumbers.contains(number)) {
                return true;
            }
            seenNumbers.add(number);
        }
        return false;
    }
}
