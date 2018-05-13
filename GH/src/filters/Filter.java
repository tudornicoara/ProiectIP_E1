package filters;

import asset.Asset;

import java.util.List;

@FunctionalInterface
public interface Filter<T extends Asset >
{
    public List<T> accept(List<T> input);
}